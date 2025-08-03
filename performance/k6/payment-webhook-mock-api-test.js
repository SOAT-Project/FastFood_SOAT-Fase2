import http from 'k6/http';
import {check} from 'k6';

const BASE_URL = 'http://localhost:8080/api';
const WEBHOOK_URL = `${BASE_URL}/mock/simulate-mercadopago-webhook`;

export const options = {
    vus: 10,
    duration: '10s',
};

export default function () {
    const payload = JSON.stringify({
        id: "56a21d4a-fc57-4636-a5c6-0e5e087ea32b",
        topic: "payment.created",
        resource: "http://mock-url.com/payment/56a21d4a-fc57-4636-a5c6-0e5e087ea32b"
    });

    const headers = {
        'Content-Type': 'application/json'
    };

    const res = http.post(WEBHOOK_URL, payload, {headers});

    check(res, {
        'status is 200 or 404': (r) => r.status === 200 || r.status === 404,
        'is JSON': (r) => r.headers['Content-Type'].includes('application/json'),
    });
}
