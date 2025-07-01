import request from '@/utils/request'

// 查询实验数据管理列表
export function listExperiment1(query) {
  return request({
    url: '/manage/experiment1/list',
    method: 'get',
    params: query
  })
}

// 查询实验数据管理详细
export function getExperiment1(id) {
  return request({
    url: '/manage/experiment1/' + id,
    method: 'get'
  })
}

// 新增实验数据管理
export function addExperiment1(data) {
  return request({
    url: '/manage/experiment1',
    method: 'post',
    data: data
  })
}

// 修改实验数据管理
export function updateExperiment1(data) {
  return request({
    url: '/manage/experiment1',
    method: 'put',
    data: data
  })
}

// 删除实验数据管理
export function delExperiment1(id) {
  return request({
    url: '/manage/experiment1/' + id,
    method: 'delete'
  })
}
