import request from "@/utils/request";

export function chat(msg) {
  return request({
    url: '/ai/o.php/' + msg,
    method: 'get',
    baseURL: process.env.BASE_API2,
  })
}

export function today() {
  return request({
    url: '/lishi/jt.php',
    method: 'get',
    baseURL: process.env.BASE_API2
  })
}

export function holiday() {
  return request({
    url: '/holiday',
    method: 'get',
    baseURL: process.env["VUE_APP_BASE_API "]
  })
}

export function hotlist() {
  return request({
    url: '/hotlist',
    method: 'get',
    baseURL: process.env["VUE_APP_BASE_API "]
  })
}

export function hotsearch() {
  return request({
    url: '/hotsearch',
    method: 'get',
    baseURL: process.env["VUE_APP_BASE_API "]
  })
}
