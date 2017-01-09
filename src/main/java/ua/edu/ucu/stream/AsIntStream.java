package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;

public class AsIntStream implements IntStream {
    private ArrayList<Integer> arrayList;
    private AsIntStream() {
        this.arrayList = new ArrayList<Integer>();
    }
    private AsIntStream(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    public static IntStream of(int... values) {
        AsIntStream stream = new AsIntStream();
        for (int i : values){
            stream.arrayList.add(i);
        }
        return stream;
    }

    @Override
    public Double average() {
        double result = sum()/count();
        return result;
    }

    @Override
    public Integer max() {
        if (arrayList.isEmpty()){
            throw new IllegalArgumentException("It is empty");
        }
        int result = arrayList.get(0);

        IntBinaryOperator operator = (l, r) -> {
            if(r > l){
                l = r;
            }
            return l;
        };
        return reduce(result, operator);

    }

    @Override
    public Integer min() {
        if (arrayList.isEmpty()){
            throw new IllegalArgumentException("It is empty");
        }
        int result = arrayList.get(0);

        IntBinaryOperator operator = (l, r) -> {
            if(r < l){
                l = r;
            }
            return l;
        };
        return reduce(result, operator);
    }

    @Override
    public long count() {
        long count;
        count = arrayList.size();
        return count;
    }

    @Override
    public Integer sum() {
        if (arrayList.isEmpty()){
            throw new IllegalArgumentException("It is empty");
        }
        int res = 0;
        IntBinaryOperator operator = (l, r) -> {
            l += r;
            return l;
        };
        return reduce(res, operator);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        AsIntStream res = new AsIntStream();   // create new stream
        for (int nums: arrayList){
            if(predicate.test(nums)){
                res.arrayList.add(nums);
            }
        }
        return res;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < arrayList.size(); i++) {
            action.accept(arrayList.get(i));
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int length = arrayList.size();
        for(int i = 0; i < length; i++) {
            arrayList.set(i, mapper.apply(arrayList.get(i)));   // at i-th position add mapper.apply(i-th element)
        }
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList flatStream = new ArrayList();
        for(int element : arrayList) {
            IntStream newElement = func.applyAsIntStream(element);
            flatStream.add(newElement);
        }
        return new AsIntStream(flatStream);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int res = identity;
        for (int element : arrayList)
            res = op.apply(res, element);
        return res;
    }

    @Override
    public int[] toArray() {
        int[] res = new int[(int)count()];
        long quantity = count();

        for(int i = 0; i < quantity; i ++){
            res[i] = arrayList.get(i);
        }
        return res;
    }

}
