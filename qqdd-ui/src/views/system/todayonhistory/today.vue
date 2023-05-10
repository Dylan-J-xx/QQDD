<template>
  <el-calendar
    v-model="value"
    @mouseenter="showTip"
    @mouseleave="hideTip">
  </el-calendar>

</template>

<script>
import {holiday, today} from "@/api/some";

export default {
  data() {
    return {
      texts: [],
      value: new Date(),
      now: new Date(),
      message: '您选择了今天!',
      tip: '您悬停在今天的日期上!'
    }
  },
  created() {
    today()
      .then(response => {
        console.log(response.data)
        this.texts = response.data.text
      })
  },
  methods: {
    showMessage() {
      this.$message({
        message: this.message,
        type: 'success'
      })
      console.log("1")
    },
    showTip() {
      this.$tooltip.open({
        content: this.tip
      })
    },
    hideTip() {
      this.$tooltip.close()
    },
    showholiday() {
      holiday()
        .then(res => {
          this.day = res.data
        })
    }
  }


}
</script>


