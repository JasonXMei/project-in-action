package builder;

public class TestBuilder {
	public static void main(String[] args) {
		ActorBuilder ab = (ActorBuilder) XMLUtil.getBean(); // 反射生成具体建造者对象

		ActorController ac = new ActorController();
		Actor actor = ac.construct(ab); // 通过指挥者创建完整的建造者对象

		String type = actor.getType();
		System.out.println(type + "的外观：");
		System.out.println("性别：" + actor.getSex());
		System.out.println("面容：" + actor.getFace());
		System.out.println("服装：" + actor.getCostume());
		System.out.println("发型：" + actor.getHairstyle());
	}
}
