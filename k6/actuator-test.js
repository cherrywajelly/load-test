import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 13, // 가상 사용자 수
    duration: '100s', // 테스트 시간
};

export default function () {
    let res = http.get("http://localhost:9000/actuator/health");
}