package com.company.libgdx.util;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, float density,
                                    World world, ContactType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Constants.getPPM(), y / Constants.getPPM());
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.getPPM(), height / 2 / Constants.getPPM());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(type);

        shape.dispose();
        return body;
    }

}
