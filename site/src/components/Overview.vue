<template>
  <div class="c-overview">
    <div v-if="loading">
      Loading...
    </div>
    <template v-for="currency in currencies" >
      <div class="c-overview__symbol" :key="currency">
        <router-link :to="`/currency/${currency}`">
          {{currency}}
        </router-link>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      loading: false,
      currencies: []
    }
  },
  mounted () {
    this.getData()
      .then((currencies) => {
        this.currencies = currencies
      })
      .catch(() => {
        console.log('Something bad happened')
      })
  },
  methods: {
    async getData () {
      try {
        const res = await fetch('http://localhost:8080/currencies', {
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
.c-overview__symbol {
  border-bottom: 1px solid black;
}
</style>
