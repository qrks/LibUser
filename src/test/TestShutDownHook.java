package test;  
public class TestShutDownHook {  
    public TestShutDownHook() {  
        doShutDownWork();  
    }  
    private void doShutDownWork() {  
        Runtime run=Runtime.getRuntime();//��ǰ Java Ӧ�ó�����ص�����ʱ����  
        run.addShutdownHook(new Thread(){ //ע���µ���������رչ���  
            @Override  
            public void run() {  
                //�������ʱ���еĲ���  
                System.out.println("�����������");  
            }  
        });  
    }  
    public static void main(String[] args) throws Exception {  
        new TestShutDownHook();  
        for (int i = 0; i < 1000; i++) { // ��������������Ҫ������� }  
            System.out.println(i);  
            if(i==500) 
            	throw new Exception("aaaa!"); 
        }  
    }  
}  