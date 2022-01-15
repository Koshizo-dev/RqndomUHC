package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.role.IRole;
import io.rqndomhax.uhcapi.role.IRoleManager;
import io.rqndomhax.uhcapi.utils.RValue;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class RolesManager implements IRoleManager {

    final RValue activeRoles = new RValue();
    final RValue roles = new RValue();

    @Override
    public void registerRole(String roleKey, IRole role) {
        roles.addObject(roleKey, role);
        enableRole(role);
    }

    @Override
    public void unregisterRole(String roleKey) {
        roles.removeObject(roleKey);
        disableRole(roleKey);
    }

    @Override
    public void unregisterRole(IRole target) {
        roles.removeObject(roles.getObjects().entrySet().stream().filter(role -> role.getValue().equals(target)).map(Map.Entry::getKey).findFirst().orElse(null));
        disableRole(target);
    }

    @Override
    public HashMap<String, IRole> getRoles() {
        return (HashMap<String, IRole>) roles.castObjects(IRole.class);
    }

    @Override
    public void enableRole(IRole role) {
        activeRoles.addObject(roles.getKey(role), role);
    }

    @Override
    public void enableRole(String roleKey) {
        activeRoles.addObject(roleKey, roles.getObject(roleKey));
    }

    @Override
    public void disableRole(IRole role) {
        activeRoles.removeObject(role);
    }

    @Override
    public void disableRole(String roleKey) {
        activeRoles.removeKey(roleKey);
    }

    @Override
    public HashMap<String, IRole> getActiveRoles() {
        return (HashMap<String, IRole>) activeRoles.castObjects(IRole.class);
    }

    @Override
    public Set<IGamePlayer> getGamePlayers(IRole role, Set<IGamePlayer> gamePlayers) {
        return gamePlayers.stream().filter(gamePlayer -> gamePlayer.getPlayerInfos().getObjects().values().stream().anyMatch(info -> role.getClass().isInstance(info))).collect(Collectors.toSet());
    }

    @Override
    public IRole getRole(IGamePlayer gamePlayer) {
        return gamePlayer.getPlayerInfos().getObjects().values().stream().filter(info -> info instanceof IRole).map(info -> (IRole) info).findFirst().orElse(null);
    }

    @Override
    public void dispatchRoles(Set<IGamePlayer> gamePlayers) {
        List<IGamePlayer> remainingPlayers = new ArrayList<>(gamePlayers);

        for (IRole role : getActiveRoles().values()) {
            if (remainingPlayers.size() == 0)
                return;
            IGamePlayer gamePlayer = remainingPlayers.get(0);
            try {
                IRole newRole = (IRole) role.getClass().getDeclaredConstructors()[0].newInstance(gamePlayer);
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
