import request from '@/utils/request'

// 查询实验进度记录列表
export function listProgress(query) {
  return request({
    url: '/manage/progress/list',
    method: 'get',
    params: query
  })
}

// 查询实验进度记录详细
export function getProgress(id) {
  return request({
    url: '/manage/progress/' + id,
    method: 'get'
  })
}

// 新增实验进度记录
export function addProgress(data) {
  return request({
    url: '/manage/progress',
    method: 'post',
    data: data
  })
}

// 修改实验进度记录
export function updateProgress(data) {
  return request({
    url: '/manage/progress',
    method: 'put',
    data: data
  })
}

// 删除实验进度记录
export function delProgress(id) {
  return request({
    url: '/manage/progress/' + id,
    method: 'delete'
  })
}
