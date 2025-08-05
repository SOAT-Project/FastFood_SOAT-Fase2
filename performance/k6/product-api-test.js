import http from 'k6/http';
import { check, group, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api';
const AUTH_URL = `${BASE_URL}/auths`;
const PRODUCTS_URL = `${BASE_URL}/products`;

export function setup() {
    const res = http.post(AUTH_URL, JSON.stringify({
        identification: 'john.doe@email.com',
    }), {
        headers: {
            'Content-Type': 'application/json',
        },
    });

    check(res, {
        'auth status is 200': (r) => r.status === 200,
        'token exists': (r) => !!r.json('access_token'),
    });

    return { token: res.json('access_token') };
}

export const options = {
    vus: 10,
    duration: '20s',
};

export default function (data) {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${data.token}`,
    };

    let createdProductId;

    group('Criar novo produto', () => {
        const payload = JSON.stringify({
            name: "Nome do Produto",
            description: "Descrição do Produto",
            value: 10.00,
            image_url: "https://exemplo.com/foto-produto.jpg",
            product_category_id: 1
        });

        const res = http.post(PRODUCTS_URL, payload, { headers });

        check(res, {
            'produto criado (201)': (r) => r.status === 201,
        });

        if (res.status === 201) {
            createdProductId = res.json('id');
        }
    });

    if (createdProductId) {
        group('Buscar produto por ID', () => {
            const res = http.get(`${PRODUCTS_URL}/${createdProductId}`, { headers });

            check(res, {
                'status 200 ou 404': (r) => r.status === 200 || r.status === 404,
            });
        });

        group('Atualizar produto', () => {
            const updatePayload = JSON.stringify({
                name: "Nome do Produto Atualizado",
                description: "Descrição do Produto Atualizado",
                value: 15.00,
                image_url: "https://exemplo-atualizado.com/foto-produto.jpg",
                product_category_id: 2
            });

            const res = http.put(`${PRODUCTS_URL}/${createdProductId}`, updatePayload, { headers });

            check(res, {
                'produto atualizado (200)': (r) => r.status === 200,
            });
        });

        group('Deletar produto', () => {
            const res = http.del(`${PRODUCTS_URL}/${createdProductId}`, null, { headers });

            check(res, {
                'produto deletado (204)': (r) => r.status === 204,
            });
        });
    }

    group('Listar produtos por categoria', () => {
        const url = `${PRODUCTS_URL}/category/1?page=0&per_page=5&sort=name&dir=asc`;
        const res = http.get(url, { headers });

        check(res, {
            'listagem OK (200)': (r) => r.status === 200,
        });
    });

    sleep(1);
}
