import http from 'k6/http';
import {check, group, sleep} from 'k6';

export const options = {
    stages: [
        {duration: '30s', target: 10},
        {duration: '1m', target: 10},
        {duration: '30s', target: 0},
    ],
};

const BASE_URL = 'http://localhost:8080/api';

export function setup() {
    const authPayload = JSON.stringify({
        identification: 'john.doe@email.com',
    });

    const authHeaders = {
        'Content-Type': 'application/json',
    };

    const res = http.post(`${BASE_URL}/auths`, authPayload, {headers: authHeaders});

    check(res, {
        'Autenticação OK (200)': (r) => r.status === 200,
        'Token presente na resposta': (r) => r.json('access_token') !== undefined,
    });

    const token = res.json('access_token');

    return {token};
}

function generateOrderPayload() {
    return JSON.stringify({
        client_public_id: '67020536-c27d-44c2-b0a0-66170fc69233',
        products: [
            {product_id: '7', quantity: 2},
        ]
    });
}

export default function (data) {
    const AUTH_TOKEN = `Bearer ${data.token}`;

    group('Criar novo pedido', () => {
        const createPayload = generateOrderPayload();
        const createHeaders = {'Content-Type': 'application/json'};

        const createRes = http.post(`${BASE_URL}/orders`, createPayload, {headers: createHeaders});

        check(createRes, {
            'Pedido criado com status 201': (r) => r.status === 201,
        });

        if (createRes.status === 201) {
            const publicId = createRes.json('publicId');

            if (publicId) {
                group('Atualizar status do pedido', () => {
                    const updatePayload = JSON.stringify({status: 'IN_PREPARATION'});

                    const updateHeaders = {
                        'Content-Type': 'application/json',
                        Authorization: AUTH_TOKEN,
                    };

                    const updateRes = http.put(`${BASE_URL}/orders/${publicId}`, updatePayload, {
                        headers: updateHeaders,
                    });

                    check(updateRes, {
                        'Status atualizado com sucesso (200)': (r) => r.status === 200,
                    });
                });
            }
        }
    });

    group('Listar pedidos (público)', () => {
        const res = http.get(`${BASE_URL}/orders?page=0&per_page=10`);

        check(res, {
            'Lista pública retornada com sucesso (200)': (r) => r.status === 200,
        });
    });

    group('Listar pedidos (staff)', () => {
        const staffHeaders = {Authorization: AUTH_TOKEN};

        const res = http.get(
            `${BASE_URL}/orders/staff?only_paid=false&search=&page=0&per_page=10&sort=orderNumber&dir=asc`,
            {headers: staffHeaders}
        );

        check(res, {
            'Lista staff retornada com sucesso (200)': (r) => r.status === 200,
        });
    });

    sleep(1);
}
