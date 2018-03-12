<template>
  <div class="c-currency">
    <router-link to="/">HOME</router-link>
    <div class="c-currency__symbol">
      {{$route.params.currency}}
    </div>
    <div class="c-currency__value">
      <span v-if="loading">Loading...</span>
      <span v-else>{{value}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Currency',
  data () {
    return {
      loading: false,
      value: []
    }
  },
  mounted () {
    this.loading = true
    this.getCurrency()
      .then((value) => {
        this.loading = false
        this.value = value
      })
      .catch(() => {
        console.log('Something bad happened')
      })
  },
  methods: {
    async getCurrency () {
      try {
        const res = await fetch(
          `http://localhost:8080/currencies/${this.$route.params.currency}`, {
            mode: 'cors',
            headers: {
              'Access-Control-Allow-Origin': '*'
            }
          })
        return await res.json()
      } catch (err) {
        console.log(err)
        return [
          'CHF'
        ]
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
