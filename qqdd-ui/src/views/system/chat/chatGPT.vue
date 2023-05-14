<template>
  <div class="chat-container">
    <!-- 对话框区域 -->
    <div class="chat-dialogue">
      <!-- 用户问题 -->
      <div v-for="(item, index) in messageList" :key="index" class="q-item"
           :class="{ 'q-right': item.type === 'question', 'q-left': item.type === 'answer' }">
        <div class="q-avatar"></div>
        <div class="q-content">
          <div class="q-text">{{ item.content }}</div>
        </div>
      </div>

      <!-- AI回答 -->
      <div v-if="AIAnswer" class="q-item q-left">
        <div class="q-avatar"></div>
        <div class="q-content">
          <div class="q-text">
            <pre v-html="highlightCode(AIAnswer)"></pre>
          </div>
        </div>
      </div>

      <!-- 小程序输入框 -->
      <div class="chat-input">
        <el-input v-model="message" placeholder="请输入您的问题" clearable></el-input>
        <el-button type="primary" @click="sendMsg">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import JsonViewer from 'vue-json-viewer'
import axios from "axios";
import {chat} from "@/api/some";
import 'prismjs/themes/prism.css'
import Prism from 'prismjs'


export default {
  components: {Prism},
  data() {
    return {
      // textMessage: '用Java代码输出打印hello world',
      // messages: [
      //   {type: 'bot', content: 'Hello! How may I help you?'}
      // ],
      message: '',
      messageList: [],
      AIAnswer: ''
    }
  },
  methods: {
    // sendMessage() {
    //   chat(this.textMessage)
    //     .then(res => {
    //       let msg = ''
    //       msg = res.data;
    //
    //       console.log(msg);
    //       this.messages.push({
    //         type: 'user',
    //         content: msg
    //       })
    //     })
    //   this.messages.push({
    //     type: 'bot',
    //     content: 'I see.'
    //   })
    //   this.textMessage = ''
    // }
    sendMsg() {
      // 将用户输入的问题加入消息列表
      this.messageList.push({content: this.message, type: 'question'})
      // 发送请求获取AI的回答
      this.getAIAnswer()
      // 将输入框清空
      this.message = ''
    },

    getAIAnswer() {
      // 发送请求获取AI的回答
      chat(this.message)
        .then(res => {
          this.AIAnswer = res.data
        })
      // 这里简单模拟一个回答
      // this.AIAnswer = '这是AI的回答'
      // 将AI回答加入消息列表
      this.messageList.push({content: this.AIAnswer, type: 'answer'})
    },
    highlightCode(code) {
      return Prism.highlight(code, Prism.languages.javascript, 'javascript')
    }
  }

}
</script>
<style scoped>
.chat-container {
  width: 100%;
  height: 100%;
  border: 1px solid #ccc;
  overflow: hidden;

}

.chat-dialogue {
  height: 530px;
  padding: 10px;
  overflow-y: auto;


}

.q-item {
  display: flex;
  margin-bottom: 10px;
}

.q-right {
  justify-content: flex-end;
}

.q-left {
  justify-content: flex-start;
}

.q-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ccc;
  margin-top: 3px;
  margin-right: 10px;
}

.q-content {
  min-width: 50px;
}

.q-text {
  max-width: 400px;
  padding: 10px;
  border-radius: 10px;
  background-color: #f5f5f5;
}

.chat-input {
  display: flex;
  align-items: center;
  margin-top: 10px;

  position: absolute;
  bottom: 0;
  width: 100%;
  height: 60px;
  padding: 5px;
  box-sizing: border-box;
}
</style>
