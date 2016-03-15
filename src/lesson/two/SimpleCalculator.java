package lesson.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SemerenkoIlya on 15.03.2016.
 */
public class SimpleCalculator {

    private Map<String, Integer> operators;
    public SimpleCalculator(){
        operators = new HashMap<>();
        operators.put("sub",0);
        operators.put("add",1);
        operators.put("mul",2);
        operators.put("div",3);
    }

    private List<String> parseArguments(String request){
        List<String> argsList = new ArrayList<>();
        int index = request.indexOf("?");
        String[] args = request.substring(index+1).split("&");
        argsList.add(args[0].substring(args[0].indexOf("=")+1));
        argsList.add(args[1].substring(args[1].indexOf("=")+1));
        argsList.add(args[2].substring(args[2].indexOf("=")+1));
        return argsList;
    }

    private int getOperation(String operator){
        return operators.get(operator);
    }

    public int operate(String request){
        List<String> operands = parseArguments(request);
        int firstOp = Integer.parseInt(operands.get(1));
        int secondOp = Integer.parseInt(operands.get(2));

        switch(getOperation(operands.get(0))){
            case 0:
                return firstOp - secondOp;
            case 1:
                return firstOp + secondOp;
            case 2:
                return firstOp * secondOp;
            case 3:
                return firstOp / secondOp;
            default:
                return 0;
        }
    }

}
