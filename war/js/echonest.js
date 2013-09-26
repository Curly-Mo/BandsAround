function getBestBio(bios) {
    var best = null;
    if (bios.length > 0) {
        best = bios[0];

        for (var i = 0; i < bios.length; i++) {
            //if (bios[i].site == 'last.fm') {
            if (bios[i].site == 'wikipedia') {
                best = bios[i];
            }
        }
    }
    return best;
}
