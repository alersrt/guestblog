const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: ['babel-polyfill', './src/web/index.tsx'],
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                options: {presets: ['env', 'react']},
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(ts|tsx)$/,
                use: 'ts-loader'
            }
        ],
    },
    resolve: {extensions: ['*', '.js', '.jsx', '.ts', '.tsx']},
    output: {
        path: path.resolve(__dirname, 'target/'),
        publicPath: '/dist/',
        filename: 'bundle.js',
    },
    devServer: {
        static: path.join(__dirname, 'src/main/resources/static/'),
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost/',
                secure: false,
            },
        },
        hot: 'only',
    },
    plugins: [new webpack.HotModuleReplacementPlugin()],
};
