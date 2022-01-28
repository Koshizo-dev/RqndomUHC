package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IRole;
import io.rqndomhax.uhcapi.game.Team;
import io.rqndomhax.uhcapi.managers.IRoleManager;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
    public Set<IGamePlayer> getGamePlayers(Class<? extends IRole> role, Set<IGamePlayer> gamePlayers) {
        return gamePlayers.stream().filter(gamePlayer -> gamePlayer.getPlayerInfos().getObjects().values().stream().anyMatch(role::isInstance)).collect(Collectors.toSet());
    }

    @Override
    public void dispatchRoles(Set<IGamePlayer> gamePlayers) {
        List<IGamePlayer> remainingPlayers = new ArrayList<>(gamePlayers);
        Set<IRole> roles = new HashSet<>();

        for (IRole role : getActiveRoles().values()) {
            if (remainingPlayers.size() == 0)
                return;
            IGamePlayer gamePlayer = remainingPlayers.get(0);
            try {
                IRole newRole = (IRole) role.getClass().getDeclaredConstructors()[0].newInstance(gamePlayer);
                gamePlayer.getPlayerInfos().addObject("role", newRole);
                roles.add(newRole);
                remainingPlayers.remove(gamePlayer);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.err.println("An error happened on the role: " + role);
                Player player = gamePlayer.getPlayer();
                if (player != null)
                    player.sendMessage(ChatColor.RED + "Un problème est survenu lors de l'attribution de votre rôle (" + role.getClass().getName() + ") !!!");
            }
        }
        for (IRole t : roles)
            t.onRoleGiven();
    }

    @Override
    public boolean activeRolesDifferentTeam() {
        Class<?> tmp = null;
        for (IRole role : getActiveRoles().values()) {
            Optional<Class<?>> team = Arrays.stream(role.getClass().getInterfaces()).filter(Team.class::isAssignableFrom).findFirst();
            if (!team.isPresent())
                continue;
            Class<?> toCompare = team.get();
            if (tmp == null) {
                tmp = toCompare;
                continue;
            }
            if (!tmp.getName().equals(toCompare.getName()))
                return (true);
        }
        return (false);
    }
}
