<template>
  <div class="c-calculator">
    <div v-if="loading">
      Loading...
    </div>
    <template v-else>
      <select name="baseCurrency" id="baseCurrency" v-model="baseCurrency">
        <option v-for="currency in currencies" :key="currency" :value="currency">
          {{currency}}
        </option>
      </select>
      <div class="c-calculator__rate">
        {{rate}}
      </div>
      <select name="targetCurrency" id="targetCurrency" v-model="targetCurrency">
        <option v-for="currency in currencies" :key="currency" :value="currency">
          {{currency}}
        </option>
      </select>
    </template>
  </div>
</template>

<script>
export default {
  name: 'Overview',
  data () {
    return {
      loading: false,
      baseCurrency: 'EUR',
      targetCurrency: 'EUR',
      currencies: []
    }
  },
  mounted () {
    this.loading = true
    this.getData()
      .then((currencies) => {
        this.loading = false
        this.currencies = currencies
      })
      .catch((err) => {
        console.error('Something bad happened', err)
      })
  },
  asyncComputed: {
    async rate () {
      console.log(this.baseCurrency, this.targetCurrency)

      if (this.baseCurrency === this.targetCurrency) {
        return 1
      }
      try {
        const baseRate = this.getCurrency(this.baseCurrency)
        const targetRate = this.getCurrency(this.targetCurrency)
        return baseRate / targetRate
      } catch (err) {
        console.error(err)
        alert('Something went wrong, sorry')
      }
    }
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
    },
    async getCurrency (currency) {
      try {
        const res = await fetch(
          `http://localhost:8080/currencies/${currency}`, {
            mode: 'cors',
            headers: {
              'Access-Control-Allow-Origin': '*'
            }
          })
        return await res.json()
      } catch (err) {
        console.log(err)
        return 1
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
