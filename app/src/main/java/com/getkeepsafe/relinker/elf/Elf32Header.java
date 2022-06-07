package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Elf32Header extends Elf.Header {
    private final ElfParser a;

    public Elf32Header(boolean z, ElfParser elfParser) throws IOException {
        this.bigEndian = z;
        this.a = elfParser;
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = elfParser.readHalf(allocate, 16L);
        this.phoff = elfParser.readWord(allocate, 28L);
        this.shoff = elfParser.readWord(allocate, 32L);
        this.phentsize = elfParser.readHalf(allocate, 42L);
        this.phnum = elfParser.readHalf(allocate, 44L);
        this.shentsize = elfParser.readHalf(allocate, 46L);
        this.shnum = elfParser.readHalf(allocate, 48L);
        this.shstrndx = elfParser.readHalf(allocate, 50L);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(int i) throws IOException {
        return new Section32Header(this.a, this, i);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(long j) throws IOException {
        return new Program32Header(this.a, this, j);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(long j, int i) throws IOException {
        return new Dynamic32Structure(this.a, this, j, i);
    }
}
