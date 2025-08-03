import http from 'k6/http';
import {check, group, sleep} from 'k6';
import {uuidv4} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export const options = {
    stages: [
        {duration: '20s', target: 5},
        {duration: '30s', target: 10},
        {duration: '10s', target: 0},
    ],
};

const BASE_URL = 'http://localhost:8080/api';

export default function () {
    const headers = {
        'Content-Type': 'application/json',
    };

    const externalReference = uuidv4();

    group('Consultar QRCode por referência', () => {
        const res = http.get(`${BASE_URL}/payments/qrcode?external_reference=${externalReference}`, {headers});

        check(res, {
            'status 200 ou 404': (r) => r.status === 200 || r.status === 404,
        });
    });

    group('Consultar status por referência', () => {
        const res = http.get(`${BASE_URL}/payments/status?external_reference=${externalReference}`, {headers});

        check(res, {
            'status 200 ou 404': (r) => r.status === 200 || r.status === 404,
        });
    });

    sleep(1);
}
