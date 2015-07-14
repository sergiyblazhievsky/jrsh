class Jrsh < Formula
  desc "A Command Line Interface for JasperReports Server"
  homepage "https://github.com/Krasnyanskiy/jrsh"
  url "https://github.com/Krasnyanskiy/jrsh/releases/download/v2.0.4/jrsh-2.0.4.zip"
  sha256 "7630fe90f4ccf15edc5ec283c73ca8689388f0fbf3ce517267bb29be5d8c89df"

  def install
    libexec.install "jrsh.jar"
    bin.write_jar_script libexec/"jrsh.jar", "jrsh"
  end

  test do
    system "#{bin}/jrsh"
  end
end