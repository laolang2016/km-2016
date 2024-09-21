/**
 * 声明页面模块列表
 *
 * 例如 index 模块, 要求如下几个文件存在
 * 1. src/build/index.js
 * 2. src/html/index.html
 * 3. src/js/index.js
 * 4. src/sass/index.scss
 */
const modules = ['index']

const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')

/**
 * 生成 entry
 * @param {string} moduleName 模块名称
 * @returns webpack entry
 */
const getModuleEntry = (moduleName) => {
    return path.resolve(__dirname, '../src/build/' + moduleName + '.js')
}

/**
 * 生成 html 插件实例
 * @param {string} moduleName 模块名称
 * @returns HtmlWebpackPlugin 实例
 */
const getModuleHtml = (moduleName) => {
    return new HtmlWebpackPlugin({
        filename: 'html/' + moduleName + '.html',
        template: './src/html/' + moduleName + '.html',
        chunks: ['common', moduleName],
        inject: true,
        hash: true
    })
}

/**
 * 导出的配置对象
 */
const moduleConfig = {
    entry: {},
    html: []
}

/**
 * 遍历模块配置列表, 生成每个模块对应的配置
 */
modules.forEach((m) => {
    moduleConfig.entry[m] = getModuleEntry(m)
    moduleConfig.html.push(getModuleHtml(m))
})

module.exports = moduleConfig
