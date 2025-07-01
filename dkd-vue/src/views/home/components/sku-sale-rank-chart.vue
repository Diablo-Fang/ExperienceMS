<template>
  <div class="box bgc3 sku-sale-rank">
    <!-- TODO: 分辨大怎么展示问UI -->
    <div class="header">
      <div class="title">
        图书热榜<span class="sub-title">{{ start }} ~ {{ end }}</span>
      </div>
    </div>
    <div class="body">
      <el-row v-for="(item, index) in skuSaleRank" :key="index">
        <el-col :span="5">
          <div :class="'top top' + (index + 1)">
            {{ index + 1 }}
          </div>
        </el-col>
        <el-col :span="13">
          <div class="sku-name" :title="item.skuName">
            {{ item.skuName }}
          </div>
        </el-col>
        <el-col :span="6">
          <div class="count">{{ item.count }}单</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script setup>
import { onMounted } from 'vue';
import dayjs from 'dayjs';
// 定义变量
const skuSaleRank = ref([
  {
    "skuId": "1",
    "skuName": "《平凡的世界》（路遥）",
    "count": 320,
    "amount": 0
  },
  {
    "skuId": "2",
    "skuName": "《明朝那些事儿》（当年明月）",
    "count": 285,
    "amount": 0
  },
  {
    "skuId": "3",
    "skuName": "《活着》（余华）",
    "count": 264,
    "amount": 0
  },
  {
    "skuId": "4",
    "skuName": "《追风筝的人》（卡勒德·胡赛尼）",
    "count": 230,
    "amount": 0
  },
  {
    "skuId": "5",
    "skuName": "《解忧杂货店》（东野圭吾）",
    "count": 178,
    "amount": 0
  },
  {
    "skuId": "6",
    "skuName": "《嫌疑人X的献身》（东野圭吾）",
    "count": 154,
    "amount": 0
  },
  {
    "skuId": "7",
    "skuName": "《白夜行》（东野圭吾）",
    "count": 117,
    "amount": 0
  },
  {
    "skuId": "8",
    "skuName": "《三体》（刘慈欣）",
    "count": 102,
    "amount": 0
  },
  {
    "skuId": "9",
    "skuName": "《围城》（钱钟书）",
    "count": 79,
    "amount": 0
  },
  {
    "skuId": "10",
    "skuName": "《挪威的森林》（村上春树）",
    "count": 57,
    "amount": 0
  }
])
const start = dayjs().startOf('month').format('YYYY.MM.DD');
const end = dayjs().endOf('day').format('YYYY.MM.DD');
</script>
<style lang="scss" scoped>
@import '@/assets/styles/variables.module.scss';

.sku-sale-rank {
  display: flex;
  flex-direction: column;
  height: calc((100vh - 120px) * 0.6);
  min-height: 538px;
  background: #FFFFFF;
  border-radius: 20px;

  .body {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-top: 20px;

    .top {
      display: inline-block;
      width: 16px;
      height: 20px;
      margin-left: 10px;
      background: url('@/assets/user-task-stats/top.png');
      text-align: center;
      font-size: 12px;
      font-weight: normal;
      color: #E9B499;
      line-height: 14px;
    }

    .top1 {
      width: 21px;
      height: 20px;
      background: url('@/assets/user-task-stats/top1.png');
      color: #8E5900;
    }

    .top2 {
      width: 21px;
      height: 20px;
      background: url('@/assets/user-task-stats/top2.png');
      color: #494949;
    }

    .top3 {
      width: 21px;
      height: 20px;
      background: url('@/assets/user-task-stats/top3.png');
      color: #CF6D3D;
    }

    .sku-name {
      height: 20px;
      font-size: 14px;
      font-weight: 500;
      color: $--color-text-primary;
      line-height: 20px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    .count {
      height: 20px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #737589;
      line-height: 20px;
      text-align: right;
    }
  }
}
</style>
