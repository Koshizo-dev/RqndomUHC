package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.role.RRole;
import io.rqndomhax.uhcapi.role.RRoleManager;
import io.rqndomhax.uhcapi.utils.RValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RolesManager extends RValue implements RRoleManager {

    @Override
    public void createRole(String roleKey, Class<? extends RRole> role) {
        addObject(roleKey, role);
    }

    @Override
    public void deleteRole(String roleKey) {
        removeObject(roleKey);
    }

    @Override
    public void deleteRole(Class<? extends RRole> target) {
        removeObject(getObjects().entrySet().stream().filter(role -> role.getValue().equals(target)).map(Map.Entry::getKey).findFirst().orElse(null));
    }

    @Override
    public HashMap<String, RRole> getRoles() {
        return null;
    }

    @Override
    public Set<RGamePlayer> getGamePlayer(RRole role) {
        return null;
    }

    @Override
    public RRole getRole(RGamePlayer gamePlayer) {
        return null;
    }

}
