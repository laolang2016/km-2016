const moduleConfig = require('./webpack.module')

// Node.js的核心模块，专门用来处理文件路径
const path = require('path')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')

const getStyleLoaders = (preProcessor) => {
    return [
        MiniCssExtractPlugin.loader,
        'css-loader',
        {
            loader: 'postcss-loader',
            options: {
                postcssOptions: {
                    plugins: [
                        'postcss-preset-env' // 能解决大多数样式兼容性问题
                    ]
                }
            }
        },
        preProcessor
    ].filter(Boolean)
}

module.exports = {
    // 入口
    // 相对路径和绝对路径都行
    entry: {
        common: path.resolve(__dirname, '../src/build/common.js'),
        ...moduleConfig.entry
    },
    // 路径别名
    resolve: {
        alias: {
            '@': path.resolve(__dirname, '../src')
        }
    },
    // 输出
    output: {
        // path: 文件输出目录，必须是绝对路径
        // path.resolve()方法返回一个绝对路径
        // __dirname 当前文件的文件夹绝对路径
        path: path.resolve(__dirname, '../dist'),
        // filename: 输出文件名
        filename: 'static/js/[name].js',
        // 自动将上次打包目录资源清空
        clean: true
    },
    // 加载器
    module: {
        rules: [
            // css
            {
                // 用来匹配 .css 结尾的文件
                test: /\.css$/,
                // use 数组里面 Loader 执行顺序是从右到左
                use: getStyleLoaders()
            },
            // sass
            {
                test: /\.s[ac]ss$/,
                use: getStyleLoaders('sass-loader')
            },
            // 图片
            {
                test: /\.(png|jpe?g|gif|webp)$/,
                type: 'asset',
                generator: {
                    // 将图片文件输出到 static/imgs 目录中
                    // 将图片文件命名 [hash:8][ext][query]
                    // [hash:8]: hash值取8位
                    // [ext]: 使用之前的文件扩展名
                    // [query]: 添加之前的query参数
                    filename: 'static/imgs/[hash:8][ext][query]'
                }
            },
            // js
            {
                test: /\.js$/,
                include: [path.resolve(__dirname, 'src/js'), path.resolve(__dirname, 'src/build')],
                loader: 'babel-loader',
                options: {
                    cacheDirectory: true, // 开启babel编译缓存
                    cacheCompression: false // 缓存文件不要压缩
                }
            }
        ]
    },
    // 插件
    plugins: [
        new CopyWebpackPlugin({
            patterns: [
                { from: './src/libs', to: 'libs' },
                { from: './src/images', to: 'images' }
            ]
        }),
        new MiniCssExtractPlugin({
            filename: 'static/css/[name].css'
        }),
        new CssMinimizerPlugin(),
        ...moduleConfig.html
    ]
}
