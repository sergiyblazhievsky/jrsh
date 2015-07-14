class Jrsh < Formula
  desc "A Command Line Interface for JasperReports Server"
  homepage "https://github.com/Krasnyanskiy/jrsh/"
  url "https://github.com/Krasnyanskiy/jrsh/releases/download/v2.0.4/jrsh-2.0.4-jar-with-dependencies.jar"

  def install
    libexec.install "jrsh-2.0.4-jar-with-dependencies.jar"
    bin.install_symlink libexec/"jrsh.sh"
  end

  test do
    system "#{bin}/jrsh"
  end
end