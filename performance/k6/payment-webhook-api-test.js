import http from 'k6/http';
import {check} from 'k6';

const BASE_URL = 'http://localhost:8080/api';
const AUTH_URL = `${BASE_URL}/auths`;
const WEBHOOK_URL = `${BASE_URL}/webhooks/mercadopago`;

let token;

export function setup() {
    const res = http.post(AUTH_URL, JSON.stringify({
        identification: 'john.doe@email.com'
    }), {
        headers: {
            'Content-Type': 'application/json'
        }
    });

    check(res, {
        'auth status is 200': (r) => r.status === 200,
        'auth has token': (r) => !!r.json('access_token'),
    });

    return {token: res.json('access_token')};
}

export const options = {
    vus: 10,
    duration: '10s',
};

export default function (data) {
    const payload = JSON.stringify({
        id: "56a21d4a-fc57-4636-a5c6-0e5e087ea32b",
        topic: "payment.created",
        resource: "http://mock-url.com/payment/56a21d4a-fc57-4636-a5c6-0e5e087ea32b"
    });

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${data.token}`
    };

    const res = http.post(WEBHOOK_URL, payload, {headers});

    check(res, {
        'status is 200 or 404': (r) => r.status === 200 || r.status === 404,
        'is JSON': (r) => r.headers['Content-Type'].includes('application/json'),
    });
}
