// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
  },
  // https://github.com/standard/standard/blob/master/docs/RULES-en.md
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  "rules": {
    // enforce semi
    "semi": ["error", "always"],
    // use 4 spaces indent
    "indent": ["error", 4],
    // make rule equal vs code auto formatting
    "space-before-function-paren": ["error", {
    "anonymous": "always",
    "named": "never"
    }],
  },
  "globals": {
    "$": true,
    "jQuery": true
  }
}
