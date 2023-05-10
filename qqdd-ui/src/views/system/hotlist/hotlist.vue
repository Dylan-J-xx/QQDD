<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>头条热搜</span>
        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
      </div>
      <div v-for="item in list" :key="item.hotindex" class="text item">
        {{ item.word }}
      </div>
    </el-card>
    <el-card class="box-card" style="float: left">
      <div slot="header" class="clearfix">
        <span>全网热搜</span>
        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
      </div>
      <div v-for="item in list2" :key="item.hotnum" class="text item">
        {{ item.title + ":" + item.digest }}
      </div>
    </el-card>
  </div>
</template>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>
<script>
import {hotlist} from "@/api/some";

export default {
  data() {
    return {
      list: [],
      list2: []
    }
  },
  mounted() {
    hotlist()
      .then(res => {
        console.log(res.data)
        this.list = res.data.list

      })
    hotsearch()
      .then(res => {
        this.list2 = res.data.list
      })
  }
}
</script>
