package Models.Animation;

import Engine.XmlParser.XmlNode;
import Engine.XmlParser.XmlParser;
import Models.Animated.AnimatedLoader;
import Models.Animated.AnimatedModelData;
import Models.Animated.GeometryLoader;
import Models.Animated.MeshData;
import Models.Animated.SkinLoader;
import Models.Animated.SkinningData;
import Models.Skeleton.SkeletonData;
import Models.Skeleton.SkeletonLoader;

public class ColladaLoader {

	public static AnimatedModelData loadColladaModel(String fileName, int maxWeights) {
		XmlNode node = XmlParser.loadXmlFile(fileName);

		SkinLoader skinLoader = new SkinLoader(node.getChild("library_controllers"), maxWeights);
		SkinningData skinningData = skinLoader.extractSkinData();

		SkeletonLoader jointsLoader = new SkeletonLoader(node.getChild("library_visual_scenes"), skinningData.jointOrder);
		SkeletonData jointsData = jointsLoader.extractBoneData();

		GeometryLoader g = new GeometryLoader(node.getChild("library_geometries"), skinningData.verticesSkinData);
		MeshData meshData = g.extractModelData();

		return new AnimatedModelData(meshData, jointsData);
	}

	public static AnimationData loadColladaAnimation(String fileName) {
		XmlNode node = XmlParser.loadXmlFile(fileName);
		XmlNode animNode = node.getChild("library_animations");
		XmlNode jointsNode = node.getChild("library_visual_scenes");
		AnimatedLoader loader = new AnimatedLoader(animNode, jointsNode);
		AnimationData animData = loader.extractAnimation();
		return animData;
	}

}
