import http from 'k6/http';

export let options = {
    vus: 14,
    duration: '100s',
};


export default function () {
    let memberId = Math.floor(options.vus);

    // API 호출
    let res = http.get('http://localhost:9000/api/v1/follow/followings', {
        headers: {
            'X-User-Id': memberId,
            'X-User-Role': 'STAFF',
        },
    });
}