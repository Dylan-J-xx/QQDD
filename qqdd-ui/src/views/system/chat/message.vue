<template>
  <div :class="message.type == 'user' ? 'self-end' : 'self-start'">
    <div
      :class="message.type == 'user' ? 'rounded-lg bg-blue-200 p-4 my-2 max-w-xs' : 'rounded-lg bg-gray-200 p-4 my-2 max-w-xs'">
      <span>{{ message.content }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    message: {
      type: Object,
      required: true
    }
  }
}
</script>
