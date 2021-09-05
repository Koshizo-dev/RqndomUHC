package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.role.RRole;
import io.rqndomhax.uhcapi.role.RRoleManager;
import io.rqndomhax.uhcapi.utils.RValue;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class RolesManager extends RValue implements RRoleManager {

    final RValue activeRoles = new RValue();

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
        return (HashMap<String, RRole>) castObjects(RRole.class);
    }

    @Override
    public void enableRole(RRole role) {
        activeRoles.addObject(getKey(role), role);
    }

    @Override
    public void enableRole(String roleKey) {
        activeRoles.addObject(roleKey, getObject(roleKey));
    }

    @Override
    public void disableRole(RRole role) {
        activeRoles.removeObject(role);
    }

    @Override
    public void disableRole(String roleKey) {
        activeRoles.removeKey(roleKey);
    }

    @Override
    public HashMap<String, RRole> getActiveRoles() {
        return (HashMap<String, RRole>) activeRoles.castObjects(RRole.class);
    }

    @Override
    public Set<RGamePlayer> getGamePlayers(RRole role, Set<RGamePlayer> gamePlayers) {
        return gamePlayers.stream().filter(gamePlayer -> gamePlayer.getPlayerInfos().getObjects().values().stream().anyMatch(info -> role.getClass().isInstance(info))).collect(Collectors.toSet());
    }

    @Override
    public RRole getRole(RGamePlayer gamePlayer) {
        return gamePlayer.getPlayerInfos().getObjects().values().stream().filter(info -> info instanceof RRole).map(info -> (RRole) info).findFirst().orElse(null);
    }

    @Override
    public void dispatchRoles(Set<RGamePlayer> gamePlayers) {
        List<RGamePlayer> remainingPlayers = new ArrayList<>(gamePlayers);

        for (RRole role : getActiveRoles().values()) {
            if (remainingPlayers.size() == 0)
                return;
            RGamePlayer gamePlayer = remainingPlayers.get(0);
            try {
                RRole newRole = (RRole) role.getClass().getDeclaredConstructors()[0].newInstance(gamePlayer);
                newRole.onRoleGiven();
                gamePlayer.getPlayerInfos().addObject("role", newRole);
                remainingPlayers.remove(gamePlayer);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
