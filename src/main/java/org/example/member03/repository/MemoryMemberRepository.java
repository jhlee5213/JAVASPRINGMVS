package org.example.member03.repository;

import org.example.member03.domain.Member;
import java.util.*;
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long nextId = 1;
    @Override
    public Member save(Member member) {
        member.setId(nextId++);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member m = store.get(id);
        return Optional.ofNullable(m);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(m -> m.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
