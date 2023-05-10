<template>
  <div class="h-screen flex flex-col">
    <header class="w-full h-16 shadow flex items-center px-4">
      <div class="text-gray-700 font-semibold text-2xl">Your Chatbot</div>
    </header>
    <div class="overflow-y-scroll flex-1 px-12 pt-4">
      <Message v-for="message in messages" :message="message"/>
    </div>
    <div class="flex items-center pl-12 pt-4">
      <textarea v-model="textMessage" rows="2" class="p-4 rounded-lg w-full focus:outline-none"></textarea>
      <button @click="sendMessage" class="ml-4 px-6 py-2 bg-blue-500 rounded-lg text-white">Send</button>
    </div>
  </div>

</template>

<script>
import Message from './message.vue'
import axios from "axios";
import {chat} from "@/api/some";

export default {
  components: {Message},
  data() {
    return {
      textMessage: '用Java代码输出打印hello world',
      messages: [
        {type: 'bot', content: 'Hello! How may I help you?'}
      ],
    }
  },
  methods: {
    sendMessage() {
      chat(this.textMessage)
        .then(res => {
          let msg = ''
          msg = res.data;

          console.log(msg);
          this.messages.push({
            type: 'user',
            content: msg
          })
        })
      this.messages.push({
        type: 'bot',
        content: 'I see.'
      })
      this.textMessage = ''
    }
  }
}
</script>
