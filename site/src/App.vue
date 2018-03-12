<template>
  <div id="app">
    <h1 class="c-headline">Currencies</h1>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin: 0 300px 0 300px;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
