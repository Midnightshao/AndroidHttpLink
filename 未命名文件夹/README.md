# AndroidHttpLink
一个高度简化的post 和 get 请求程序。 但是很方便

        new Thread(new Runnable() {
            @Override
            public void run() {

                map=new HashMap<String,Object>();

                map.put("id",1);

                map.put("shao",2);

                HttpClient httpClient=new HttpClient();

                httpClient.addHeader("queen","hahha");
                //post
                textView.setText(httpClient.PostHttpClient("http://192.168.1.107:8080/dataes/loginServlet",map));
                //get
                textView.setText(httpClient.GetHttpClient("http://192.168.1.107:8080/dataes/loginServlet?id=1&shao=邵广昊"));

            }
        }).start();
        //这样就可以使用了
