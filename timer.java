public class timer { // this class is used to keep track of time
        int time = 0;
        public timer(){}
        public timer(int time){this.time = time;}
        public void update(){time++;}
        public int getTime(){return time;}
        public void reset(){time = 0;}
        public void setTime(int time){this.time = time;}
}