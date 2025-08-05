import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [
        {duration: '30s', target: 10}, // sobe até 10 usuários virtuais
        {duration: '1m', target: 10},  // mantém por 1 minuto
        {duration: '30s', target: 0},  // finaliza
    ],
};

const BASE_URL = 'http://localhost:8080/api';

export default function () {
    const payload = JSON.stringify({
        "identification": "john.doe@email.com"
    });

    const headers = {
        'Content-Type': 'application/json',
    };

    const res = http.post(`${BASE_URL}/auths`, payload, {headers});

    check(res, {
        'status é 200': (r) => r.status === 200
    });

    sleep(1);
}
