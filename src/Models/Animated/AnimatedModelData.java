package Models.Animated;

import Models.Skeleton.SkeletonData;

public class AnimatedModelData {

	private final SkeletonData joints;
	private final MeshData mesh;
	
	public AnimatedModelData(MeshData mesh, SkeletonData joints){
		this.joints = joints;
		this.mesh = mesh;
	}
	
	public SkeletonData getJointsData(){
		return joints;
	}
	
	public MeshData getMeshData(){
		return mesh;
	}
	
}
