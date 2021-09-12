package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.role.RRole;
import io.rqndomhax.uhcapi.role.RRoleManager;
import io.rqndomhax.uhcapi.utils.RValue;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class RolesManager implements RRoleManager {

    final RValue activeRoles = new RValue();
    final RValue roles = new RValue();
    final RValue teams = new RValue();

    @Override
    public void addTeam(String teamKey, Class<?> team) {
        teams.addObject(teamKey, team);
    }

    @Override
    public void removeTeam(Class<?> team) {
        teams.removeObject(team);
    }

    @Override
    public void removeTeam(String teamKey) {
        teams.removeKey(teamKey);
    }

    @Override
    public void createRole(String roleKey, RRole role) {
        roles.addObject(roleKey, role);
    }

    @Override
    public void deleteRole(String roleKey) {
        roles.removeObject(roleKey);
        disableRole(roleKey);
    }

    @Override
    public void deleteRole(RRole target) {
        roles.removeObject(roles.getObjects().entrySet().stream().filter(role -> role.getValue().equals(target)).map(Map.Entry::getKey).findFirst().orElse(null));
        disableRole(target);
    }

    @Override
    public HashMap<String, RRole> getRoles() {
        return (HashMap<String, RRole>) roles.castObjects(RRole.class);
    }

    @Override
    public void enableRole(RRole role) {
        activeRoles.addObject(roles.getKey(role), role);
    }

    @Override
    public void enableRole(String roleKey) {
        activeRoles.addObject(roleKey, roles.getObject(roleKey));
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
