<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'Git',
  data() {
    return {
      url: 'https://github.com/Dylan-J-xx/QQDD'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
