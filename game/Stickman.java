package game;

import game.objectclasses.*;

public class Stickman {
    public Element element;
    public String name;
    public String color;
    public Model model;
    public double health = 100;

    public Stickman(Game game, Element element, String name, String color) {
        this.element = element;
        this.name = name;
        this.color = color;
        this.model = new Model();
        // head, humanoidrootpart, torso, leftarm, rightarm, leftleg, rightleg
        try {

            Part head = (Part) Instance.create("Part", game.workspace);
            head.size = new Vector2D(20, 20);
            head.position = new Vector2D(0, 0);
            head.brickColor = color;
            head.name = "Head";
            head.partType = "Eliipse";

            Part humanoidrootpart = (Part) Instance.create("Part", game.workspace);
            humanoidrootpart.size = new Vector2D(20, 20);
            humanoidrootpart.position = new Vector2D(0, 0);
            humanoidrootpart.brickColor = color;
            humanoidrootpart.name = "HumanoidRootPart";
            humanoidrootpart.partType = "Rectangle";

            Part torso = (Part) Instance.create("Part", game.workspace);
            torso.size = new Vector2D(20, 20);
            torso.position = new Vector2D(0, 0);
            torso.brickColor = color;
            torso.name = "Torso";
            torso.partType = "Rectangle";

            Part leftarm = (Part) Instance.create("Part", game.workspace);
            leftarm.size = new Vector2D(20, 20);
            leftarm.position = new Vector2D(0, 0);
            leftarm.brickColor = color;
            leftarm.name = "LeftArm";

            Part rightarm = (Part) Instance.create("Part", game.workspace);
            rightarm.size = new Vector2D(20, 20);
            rightarm.position = new Vector2D(0, 0);
            rightarm.brickColor = color;
            rightarm.name = "RightArm";

            Part leftleg = (Part) Instance.create("Part", game.workspace);
            leftleg.size = new Vector2D(20, 20);
            leftleg.position = new Vector2D(0, 0);
            leftleg.brickColor = color;
            leftleg.name = "LeftLeg";

            Part rightleg = (Part) Instance.create("Part", game.workspace);
            rightleg.size = new Vector2D(20, 20);
            rightleg.position = new Vector2D(0, 0);
            rightleg.brickColor = color;
            rightleg.name = "RightLeg";

        } catch (Exception e) {
            System.out.println("Error creating stickman");
        }
    }

}
