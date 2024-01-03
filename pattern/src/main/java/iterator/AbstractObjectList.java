package iterator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObjectList {
	protected List<String> objects = new ArrayList<String>();  
	  
    public AbstractObjectList(List<String> objects) {  
        this.objects = objects;  
    }  
      
    public void addObject(String obj) {  
        this.objects.add(obj);  
    }  
      
    public void removeObject(String obj) {  
        this.objects.remove(obj);  
    }  
      
    public List<String> getObjects() {  
        return this.objects;  
    }  
      
    //声明创建迭代器对象的抽象工厂方法  
    public abstract AbstractIterator createIterator(); 
}
