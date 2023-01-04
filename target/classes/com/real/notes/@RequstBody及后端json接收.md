# 前端传递json后端如何接收

前端 json分为ajax默认json和类型为application/json，当前端传递的为**默认json格式后端只需要对应的参数名即可接收**；当前端传递
application/json格式时后端需要使用@RequestBody注解标注参数 **@RequestBody注解会提前自动将json中的数据与对象参数名对应并调用
其setter方法将值注入** 

使用@RequestBody注解是从前端的消息体中获取数据的，**也就意味着当前端使用GET请求时后端使用@RequestBody是无法拿到数据的（GET请求
会将请求数据拼接到url中，此时后端应该用@RequestParam接收或者直接参数接收** 