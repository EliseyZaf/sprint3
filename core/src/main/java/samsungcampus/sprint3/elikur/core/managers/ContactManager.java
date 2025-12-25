package samsungcampus.sprint3.elikur.core.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.Box;
import samsungcampus.sprint3.elikur.core.model.GameObject;
import samsungcampus.sprint3.elikur.core.model.objects.ShipObject;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class ContactManager {

    World world;

    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                int combinedBits = cDef | cDef2;

                boolean isTrashAndBullet = (combinedBits == (GameSetting.TRASH_BIT | GameSetting.BULLET_BIT));
                boolean isTrashAndShip   = (combinedBits == (GameSetting.TRASH_BIT | GameSetting.SHIP_BIT));
                boolean isBoxAndShip     = (combinedBits == (GameSetting.BOX_BIT | GameSetting.SHIP_BIT));

                if (isTrashAndShip) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();

                    if (fixA.getUserData() instanceof Trash)
                         ((Trash) fixA.getUserData()).die();
                    else ((Trash) fixB.getUserData()).die();
                }

                if (isTrashAndBullet) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();

                    if (fixA.getUserData() instanceof Trash)
                        GameScreen.addDamageNum(new Vector2(((Trash) fixA.getUserData()).getX(), ((Trash) fixA.getUserData()).getY()), String.valueOf(GameScreen.player.damage));
                    if (fixB.getUserData() instanceof Trash)
                        GameScreen.addDamageNum(new Vector2(((Trash) fixB.getUserData()).getX(), ((Trash) fixB.getUserData()).getY()), String.valueOf(GameScreen.player.damage));
                }

                if (isBoxAndShip) {
                    if (fixA.getUserData() instanceof Box)
                         ((Box) fixA.getUserData()).hit();
                    else ((Box) fixB.getUserData()).hit();
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
