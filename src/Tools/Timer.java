package Tools;

public class Timer {
	
	float time, speed;
	float buffer;
	boolean finish;
	
	public Timer(float speed, float time) {
		this.speed = speed;
		this.time = time;
	}
	
	public void update() {
		if(buffer < time) {
			buffer += speed;
		}else {
			finish = true;
		}
	}
	
	public void reset() {
		buffer = 0;
		finish = false;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isFinish() {
		return finish;
	}

	public float getBuffer() {
		return buffer;
	}
	
}
