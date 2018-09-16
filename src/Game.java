import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;


public class Game {

    public class Arr {

        public int[] getData() {
            return data;
        }

        public void setData(int[] data) {
            this.data = data;
        }

        private int[] data;

        Arr(int[] obj){
            this.data = obj;
        }

        @Override
        public int hashCode() {
            Object[] src = new Object[this.data.length+1];
            ArrayList ar = new ArrayList();
            for(int t : this.data){ ar.add(t); }

            src = ar.toArray();

            return Arrays.deepHashCode(src);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null){return false;}
            //int[] arr = new int[]{ Array.getByte(obj,0), Array.getByte(obj,1)} ;

            Arr arr = (Arr)obj;
            if(Arrays.equals(this.data, arr.getData())){ return true; }

            return false;

        }


    }
    Map<Arr, ShashKa> _shashka = new HashMap<>();

    public class ShashKa {

        public byte type = 0;
        public int color;
        int i;


        ShashKa(int color) {
            this.color = color;
        }

        public void walk(byte[] direction) {
            // pos[0] += direction[0];
            //pos[1] += direction[1];
        }


        public byte[][] applyToMove(int pos[]) {


            byte[][] nearS = new byte[][]{{0, 0, 0, 0}, {0, 0, 0, 0}};

            if(pos[0] == 0){ nearS[0][2]=1;nearS[0][3]=1;nearS[1][2]=1;nearS[1][3]=1;}//IIIIIIIIIIIII
            if(pos[0] == 1){ nearS[0][3]=1;nearS[1][3]=1;}

            if(pos[0] == 7){ nearS[0][0]=1;nearS[0][1]=1; nearS[1][0]=1;nearS[1][1]=1;}
            if(pos[0] == 6){ nearS[0][0]=1;nearS[1][0]=1;}


            if(pos[1] == 7){ nearS[0][2]=1;nearS[0][3]=1;nearS[1][1]=1;nearS[1][0]=1;}//JJJJJJJJJ
            if(pos[1] == 6){ nearS[0][3]=1;nearS[1][0]=1;}

            if(pos[1] == 0){ nearS[0][0]=1;nearS[0][1]=1; nearS[1][2]=1;nearS[1][3]=1;}
            if(pos[1] == 1){ nearS[0][0]=1;nearS[1][3]=1;}


            byte[][] nearMove = new byte[][]{{0, 0, 0, 0}, {0, 0, 0, 0}};

            for (var position : _shashka.keySet()) {

                if (Arrays.equals(position.getData(), new int[]{pos[0] + 1, pos[1] - 1})) {nearS[0][1] = 1;}//c
                if (Arrays.equals(position.getData(), new int[]{pos[0] + 1, pos[1] + 1})) {nearS[1][1] = 1;}//g
                if (Arrays.equals(position.getData(), new int[]{pos[0] + 2, pos[1] - 2})) {nearS[0][0] = 1;}//d
                if (Arrays.equals(position.getData(), new int[]{pos[0] + 2, pos[1] + 2})) {nearS[1][0] = 1;}//h
                if (Arrays.equals(position.getData(), new int[]{pos[0] - 1, pos[1] - 1})) {nearS[1][2] = 1;}//f
                if (Arrays.equals(position.getData(), new int[]{pos[0] - 1, pos[1] + 1})) {nearS[0][2] = 1;}//b
                if (Arrays.equals(position.getData(), new int[]{pos[0] - 2, pos[1] - 2})) {nearS[1][3] = 1;}//e
                if (Arrays.equals(position.getData(), new int[]{pos[0] - 2, pos[1] + 2})) {nearS[0][3] = 1;}//a

            }
            //int[]
             //System.out.print(_shashka.get(new int[]{pos[0]-1,pos[1]+1}).type != this.type);

            //ROOLS TRUE
            if (this.color == 1){//BLACK
                if(nearS[1][0] == 0 && nearS[1][1] == 1){   nearMove[1][0] = 1; } //e
                if(nearS[1][1] == 0){ nearMove[1][1] = 1; } //f

                if(nearS[0][1] == 1 && nearS[0][0] == 0){   nearMove[0][0] = 1; } //a
                if(nearS[0][1] == 0){nearMove[0][1] = 1; } //b

                if(nearS[0][2] == 1 && nearS[0][3] == 0){   nearMove[0][3] = 1; } //d
                if(nearS[0][2] == 0){nearMove[0][2] = 1; } //c

                if(nearS[1][2] == 1 && nearS[1][3] == 0){   nearMove[1][3] = 1; } //h
                if(nearS[1][2] == 0){   nearMove[1][2] = 1; } //g
            }

            return  nearMove;

        }

    }


    byte[][] map = new byte[8][8];
    //byte user = 0;

    public Game() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((i+j) % 2 == 1 ) {
                    map[i][j]=1;
                    if (i < 3) {
                        _shashka.put(new Arr(new int[]{i, j}), new ShashKa(0));//WHITE
                        map[i][j] = 2;

                    }
                    if (i > 4) {
                        _shashka.put(new Arr(new int[]{i, j}), new ShashKa(1));//BLACK
                        map[i][j] = 3;
                    }

                }
            }
        }

    }

    public void print(byte[][] arr, int leng, int hieg) {

        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < hieg; j++) {
                //if(i == 1 && j == 3) { System.out.print("a");}
                //if(i == 3 && j == 1) { System.out.print("b");}
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void start(Game game){

        game.print(game.map,8,8);


        for(var Key : game._shashka.keySet()){
            Arr ar = new Arr(new int[]{7,0});
            if( ar.equals(Key)){
                System.out.println(game._shashka.get(ar) + " that    " + Key.hashCode() + " /////////////   " + ar.hashCode() );
            }
        }

        for(var Key : game._shashka.keySet()){
            //System.out.println(key[0] + " " + key[1]);

            if(Arrays.equals(new int[] {6,1}, Key.getData())) {
                // System.out.println(game._shashka);

                var where = game._shashka.get(Key);
                System.out.println(where.getClass() + " " + where.equals(null) + " " + where.color);

                game.print(where.applyToMove(Key.getData()), 2,4);
            }
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start(game);

    }

}
