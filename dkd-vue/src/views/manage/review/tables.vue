<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="card">
      <!-- 表1数据 -->
      <el-tab-pane label="1.负反馈放大器数据记录" name="table1">
        <div>
          <p>表1（图1 静态工作点）</p>
        </div>
        <el-table :data="table1_1Data" border>
          <el-table-column prop="level" label="级别"></el-table-column>
          <el-table-column prop="vc1Field" label="VC"></el-table-column>
          <el-table-column prop="vb1Field" label="VB"></el-table-column>
          <el-table-column prop="ve1Field" label="VE"></el-table-column>
        </el-table>
        <el-table :data="table1_2Data" border :show-header="false">
          <el-table-column prop="level" label="级别"></el-table-column>
          <el-table-column prop="vc2Field" label="VC"></el-table-column>
          <el-table-column prop="vb2Field" label="VB"></el-table-column>
          <el-table-column prop="ve2Field" label="VE"></el-table-column>
        </el-table>
        <br>
        <div>
          <p>表2（图1 动态参数）</p>
        </div>
        <el-table :data="table2_1Data" border>
          <el-table-column prop="status" label="参数状态"></el-table-column>
          <el-table-column prop="vs1Field" label="Vs"></el-table-column>
          <el-table-column prop="rsField" label="Rs"></el-table-column>
          <el-table-column prop="viField" label="Vi"></el-table-column>
          <el-table-column prop="fField" label="f"></el-table-column>
          <el-table-column prop="vol1Field" label="Vol"></el-table-column>
          <el-table-column prop="avl1Field" label="Avl"></el-table-column>
          <el-table-column prop="vo1Field" label="Vo"></el-table-column>
          <el-table-column prop="av1Field" label="Av"></el-table-column>
          <el-table-column prop="ri1Field" label="Ri"></el-table-column>
          <el-table-column prop="ro1Field" label="Ro"></el-table-column>
          <el-table-column prop="vo3Field" label="Vo"></el-table-column>
          <el-table-column prop="av3Field" label="Av"></el-table-column>
          <el-table-column prop="w1Field" label="W"></el-table-column>
        </el-table>
        <el-table :data="table2_2Data" border :show-header="false">
          <el-table-column prop="status" label="参数状态"></el-table-column>
          <el-table-column prop="vs2Field" label="Vs"></el-table-column>
          <el-table-column prop="rsField" label="Rs"></el-table-column>
          <el-table-column prop="viField" label="Vi"></el-table-column>
          <el-table-column prop="fField" label="f"></el-table-column>
          <el-table-column prop="vol2Field" label="Vol"></el-table-column>
          <el-table-column prop="avl2Field" label="Avl"></el-table-column>
          <el-table-column prop="vo2Field" label="Vo"></el-table-column>
          <el-table-column prop="av2Field" label="Av"></el-table-column>
          <el-table-column prop="ri2Field" label="Ri"></el-table-column>
          <el-table-column prop="ro2Field" label="Ro"></el-table-column>
          <el-table-column prop="vo4Field" label="Vo"></el-table-column>
          <el-table-column prop="av4Field" label="Av"></el-table-column>
          <el-table-column prop="w2Field" label="W"></el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 表2数据 -->
      <el-tab-pane label="2.差分放大器数据记录" name="table2">
        <div>
          <p>表3（图2 静态工作点）</p>
        </div>
        <el-table :data="table3_1Data" border>
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="vc11Field" label="VC1"></el-table-column>
          <el-table-column prop="vc21Field" label="VC2"></el-table-column>
          <el-table-column prop="vb11Field" label="VB1"></el-table-column>
          <el-table-column prop="vb21Field" label="VB2"></el-table-column>
          <el-table-column prop="ve11Field" label="VE1"></el-table-column>
          <el-table-column prop="ve21Field" label="VE2"></el-table-column>
        </el-table>
        <el-table :data="table3_2Data" border :show-header="false">
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="vc12Field" label="VC1"></el-table-column>
          <el-table-column prop="vc22Field" label="VC2"></el-table-column>
          <el-table-column prop="vb12Field" label="VB1"></el-table-column>
          <el-table-column prop="vb22Field" label="VB2"></el-table-column>
          <el-table-column prop="ve12Field" label="VE1"></el-table-column>
          <el-table-column prop="ve22Field" label="VE2"></el-table-column>
        </el-table>
        <br>
        <div>
          <p>表4（图2 动态参数）</p>
        </div>
        <el-table :data="table4_1Data" border>
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="signal" label="输入信号类型"></el-table-column>
          <el-table-column prop="vs" label="VS"></el-table-column>
          <el-table-column prop="vo11Field" label="VO1"></el-table-column>
          <el-table-column prop="vo21Field" label="VO2"></el-table-column>
          <el-table-column prop="vo1Field" label="VO"></el-table-column>
          <el-table-column prop="a1Field" label="双端输出电压增益"></el-table-column>
          <el-table-column prop="k1Field" label="KCMR"></el-table-column>
        </el-table>
        <el-table :data="table4_2Data" border :show-header="false">
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="signal" label="输入信号类型"></el-table-column>
          <el-table-column prop="vs" label="VS"></el-table-column>
          <el-table-column prop="vo12Field" label="VO1"></el-table-column>
          <el-table-column prop="vo22Field" label="VO2"></el-table-column>
          <el-table-column prop="vo2Field" label="VO"></el-table-column>
          <el-table-column prop="a2Field" label="双端输出电压增益"></el-table-column>
          <el-table-column prop="k2Field" label=""></el-table-column>
        </el-table>
        <el-table :data="table4_3Data" border >
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="signal" label="输入信号类型"></el-table-column>
          <el-table-column prop="vs" label="VS"></el-table-column>
          <el-table-column prop="vo13Field" label="VO1"></el-table-column>
          <el-table-column prop="vo23Field" label="VO2"></el-table-column>
          <el-table-column prop="vo3Field" label="VO"></el-table-column>
          <el-table-column prop="a3Field" label="双端输出电压增益"></el-table-column>
          <el-table-column prop="k3Field" label="KCMR"></el-table-column>
        </el-table>
        <el-table :data="table4_4Data" border :show-header="false">
          <el-table-column prop="type" label="电路形式"></el-table-column>
          <el-table-column prop="signal" label="输入信号类型"></el-table-column>
          <el-table-column prop="vs" label="VS"></el-table-column>
          <el-table-column prop="vo14Field" label="VO1"></el-table-column>
          <el-table-column prop="vo24Field" label="VO2"></el-table-column>
          <el-table-column prop="vo4Field" label="VO"></el-table-column>
          <el-table-column prop="a4Field" label="双端输出电压增益"></el-table-column>
          <el-table-column prop="k4Field" label=""></el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 表3数据 -->
      <el-tab-pane label="3.差分放大器中观察Vi1与VO1和VO2的相位关系并记录波形" name="table3">
        <!-- 图片展示框 -->
        <div class="image-preview" style="margin-top: 20px;">
          <div v-if="vi1vo1Image">
            <el-image
                :src="vi1vo1Image"
                :preview-src-list="[vi1vo1Image]"
                fit="contain"
                style="max-width: 100%; max-height: 100%;">
            </el-image>
          </div>
          <div v-else>
            <p>未上传图片</p>
          </div>
          <p style="text-align:center; margin-top: 10px;">Vi1与Vo1波形对比</p>
        </div>
        <br>
        <div class="image-preview" style="margin-top: 20px;">
          <div v-if="vi1vo2Image">
            <el-image
                :src="vi1vo2Image"
                :preview-src-list="[vi1vo2Image]"
                fit="contain"
                style="max-width: 100%; max-height: 100%;">
            </el-image>
          </div>
          <div v-else>
            <p>未上传图片</p>
          </div>
          <p style="text-align:center; margin-top: 10px;">Vi1与Vo2波形对比</p>
        </div>
      </el-tab-pane>

      <!-- 表4数据 -->
      <el-tab-pane label="5.运算放大器反向比例电路" name="table4">
        <div>
          <p>用万用表的档位选择</p>
        </div>
        <el-table :data="table5_3Data" border >
          <el-table-column prop="current" label="测量VA、VB信号时使用的万用表档位"></el-table-column>
        </el-table>
        <br>
        <div>
          <p>表5 反向比例运算电路</p>
        </div>
        <el-table :data="table5_1Data" border>
          <el-table-column prop="vi" label="Vi"></el-table-column>
          <el-table-column prop="vi1Field" label="Vo"></el-table-column>
          <el-table-column prop="vo1Field" label="运放序号"></el-table-column>
        </el-table>
        <el-table :data="table5_2Data" border :show-header="false">
          <el-table-column prop="vi" label="Vi"></el-table-column>
          <el-table-column prop="vi2Field" label="Vi1"></el-table-column>
          <el-table-column prop="vo2Field" label="Vo1"></el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 表5数据 -->
      <el-tab-pane label="6.运算放大器三角波发生器" name="table5">
        <div class="image-preview" style="margin-top: 20px;">
          <div v-if="vo1vo2Image">
            <el-image
                :src="vo1vo2Image"
                :preview-src-list="[vo1vo2Image]"
                fit="contain"
                style="max-width: 100%; max-height: 100%;">
            </el-image>
          </div>
          <div v-else>
            <p>未上传图片</p>
          </div>
          <p style="text-align:center; margin-top: 10px;">Vo1与Vo2波形对比</p>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup name="ExperimentTables">
import { ref, onMounted } from 'vue';
import { listExperiment1, getExperiment1} from '@/api/manage/experiment1';
import { useRoute } from 'vue-router';

const route = useRoute();
const activeTab = ref('table1');
const table1_1Data = ref([]);
const table1_2Data = ref([]);
const table2_1Data = ref([]);
const table2_2Data = ref([]);
const table3_1Data = ref([]);
const table3_2Data = ref([]);
const table4_1Data = ref([]);
const table4_2Data = ref([]);
const table4_3Data = ref([]);
const table4_4Data = ref([]);
const table5_1Data = ref([]);
const table5_2Data = ref([]);
const table5_3Data = ref([]);
const vi1vo1Image = ref('');
const vi1vo2Image = ref('');
const vo1vo2Image = ref('');

// 获取实验数据
function getExperimentData(studentId) {
  // 根据学生ID查询实验数据
  getExperiment1(studentId).then(response => {
    // 按字段分类到不同表格
    table1_1Data.value = [{
      level: '第一级',
      vc1Field: response.data.table1vc1Field,
      vb1Field: response.data.table1vb1Field,
      ve1Field: response.data.table1ve1Field
    }];

    table1_2Data.value = [{
      level: '第二级',
      vc2Field: response.data.table1vc2Field,
      vb2Field: response.data.table1vb2Field,
      ve2Field: response.data.table1ve2Field
    }];

    table2_1Data.value = [{
      status: '基本放大',
      vs1Field: response.data.table2vs1Field,
      rsField: 10,
      viField:5,
      fField:'1K',
      vol1Field: response.data.table2vol1Field,
      avl1Field: response.data.table2avl1Field,
      vo1Field: response.data.table2vo1Field,
      av1Field: response.data.table2av1Field,
      ri1Field: response.data.table2ri1Field,
      ro1Field: response.data.table2ro1Field,
      vo3Field: response.data.table2vo3Field,
      av3Field: response.data.table2av3Field,
      w1Field: response.data.table2w1Field
    }];

    table2_2Data.value = [{
      status: '负反馈放大',
      vs2Field: response.data.table2vs2Field,
      rsField: 10,
      viField:5,
      fField:'1K',
      vol2Field: response.data.table2vol2Field,
      avl2Field: response.data.table2avl2Field,
      vo2Field: response.data.table2vo2Field,
      av2Field: response.data.table2av2Field,
      ri2Field: response.data.table2ri2Field,
      ro2Field: response.data.table2ro2Field,
      vo4Field: response.data.table2vo4Field,
      av4Field: response.data.table2av4Field,
      w2Field: response.data.table2w2Field
    }];

    table3_1Data.value = [{
      type: "典型差放",
      vc11Field: response.data.table3vc11Field,
      vc21Field: response.data.table3vc21Field,
      vb11Field: response.data.table3vb11Field,
      vb21Field: response.data.table3vb21Field,
      ve11Field: response.data.table3ve11Field,
      ve21Field: response.data.table3ve21Field
    }];

    table3_2Data.value = [{
      type: "恒流源差放",
      vc12Field: response.data.table3vc12Field,
      vc22Field: response.data.table3vc22Field,
      vb12Field: response.data.table3vb12Field,
      vb22Field: response.data.table3vb22Field,
      ve12Field: response.data.table3ve12Field,
      ve22Field: response.data.table3ve22Field
    }];

    table4_1Data.value = [{
      type: "典型差放",
      signal: "差模",
      vs: "30mV",
      vo11Field: response.data.table4vo11Field,
      vo21Field: response.data.table4vo21Field,
      vo1Field: response.data.table4vo1Field,
      a1Field: response.data.table4a1Field,
      k1Field: response.data.table4k1Field
    }];

    table4_2Data.value = [{
      type: null,
      signal: "共模",
      vs: "1",
      vo12Field: response.data.table4vo12Field,
      vo22Field: response.data.table4vo22Field,
      vo2Field: response.data.table4vo2Field,
      a2Field: response.data.table4a2Field,
      k2Field: null
    }];

    table4_3Data.value = [{
      type: "恒流源差放",
      signal: "差模",
      vs: "50mV",
      vo13Field: response.data.table4vo13Field,
      vo23Field: response.data.table4vo23Field,
      vo3Field: response.data.table4vo2Field,
      a3Field: response.data.table4a3Field,
      k3Field: response.data.table4k2Field
    }];

    table4_4Data.value = [{
      type: null,
      signal: "共模",
      vs: "1",
      vo14Field: response.data.table4vo14Field,
      vo24Field: response.data.table4vo24Field,
      vo4Field: response.data.table4vo4Field,
      a4Field: response.data.table4a4Field,
      k4Field: null
    }];

    table5_1Data.value = [{
      vi: "VA=1.2v VB=0.6v",
      vi1Field: response.data.table5vi1Field,
      vo1Field: response.data.table5vo1Field
    }];

    table5_2Data.value = [{
      vi: "VA=1.2v VB=0.6v",
      vi2Field: response.data.table5vi2Field,
      vo2Field: response.data.table5vo2Field
    }];

    table5_3Data.value = [{
      current: response.data.currentText
    }];

    if(response.data.vi1vo1Image){
      vi1vo1Image.value = "http://localhost/dev-api" + response.data.vi1vo1Image;
    }else{
      vi1vo1Image.value = null;
    }
    if(response.data.vi1vo2Image){
      vi1vo2Image.value = "http://localhost/dev-api" + response.data.vi1vo2Image;
    }else{
      vi1vo2Image.value = null;
    }
    if(response.data.vo1vo2Image){
      vo1vo2Image.value = "http://localhost/dev-api" + response.data.vo1vo2Image;
    }else{
      vo1vo2Image.value = null;
    }

  });
}

function getExperimentDataById(row) {
  reset();
  const _id = row.id || ids.value
  getExperiment1(_id).then(response => {
    form.value = response.data;

    title.value = "修改实验数据管理";
  });
}

onMounted(() => {
  const studentId = route.query.studentId;
  //const studentId = 1;
  if (studentId) {
    getExperimentData(studentId);
  }
  //getExperimentData(studentId);
});
</script>