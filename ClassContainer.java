import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    Map<String, Class> groups;

    public void addClass(String name, int size){
        Class c = new Class(name, size);
        groups.put(name, c);
    }

    public void removeClass(String name){
        groups.remove(name);
    }

    public List<Class> findEmpty(){
        List<Class> emptyList = new ArrayList<>();
        for (String key : groups.keySet()){
            if (groups.get(key).size == 0) {
                emptyList.add(groups.get(key));
            }

        }
        return emptyList;
    }

    public void summary(){
        for (String key : groups.keySet()){
            System.out.println(groups.get(key));
        }
    }

}
