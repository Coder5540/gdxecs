package badlogic.demo.ecs.components;

import com.artemis.Component;

public class Renderable extends Component{
	private String textureId;

	public Renderable() {
		super();
	}

	public String getTextureId() {
		return textureId;
	}

	public void setTextureId(String textureId) {
		this.textureId = textureId;
	}
	
}
