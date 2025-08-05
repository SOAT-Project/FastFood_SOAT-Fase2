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


function generateUniqueNumber() {
    return Math.floor(Math.random() * 1000000000);
}

export default function () {
    group('Identificar cliente por CPF', () => {
        const identifyPayload = JSON.stringify({
            identification: '12345678900',
        });

        const headers = {
            'Content-Type': 'application/json',
        };

        const res = http.post(`${BASE_URL}/clients/identify`, identifyPayload, {headers});

        check(res, {
            'status é 200 ou 404': (r) => r.status === 200 || r.status === 404,
        });
    });

    group('Criar novo cliente', () => {
        const uniqueNum = generateUniqueNumber();

        const createPayload = JSON.stringify({
            cpf: String(10000000000 + uniqueNum),
            name: `Cliente Teste ${uniqueNum}`,
            email: `cliente${uniqueNum}@exemplo.com`,
        });

        const headers = {
            'Content-Type': 'application/json',
        };

        const res = http.post(`${BASE_URL}/clients`, createPayload, {headers});

        check(res, {
            'status é 201': (r) => r.status === 201,
        });
    });

    sleep(1);
}
