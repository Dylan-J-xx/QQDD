<template>
  <div style="display: flex; flex-direction: row;">
    <el-card class="box-card" style="flex: 0.5; margin-right: 20px;">
      <div slot="header" class="clearfix">
        <span>头条热搜</span>
        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
      </div>
      <div v-for="item in list" :key="item.hotindex" class="text item">
        {{ item.word }}
      </div>
    </el-card>
    <el-card class="box-card" style="flex: 1">
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

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
<script>
import {hotlist} from "@/api/some";
import {hotsearch} from "@/api/some";

export default {
  data() {
    return {
      list: [],
      list2: []
    }
  },
  mounted() {
    this.getlist();
    this.search();
  },
  methods: {
    getlist() {
      hotlist()
        .then(res => {
          this.list = res.data.list

        })
    },
    search() {
      hotsearch()
        .then(res => {
          console.log(res.data2.list)
          this.list2 = res.data2.list
        })
    }
  }
}
</script>
