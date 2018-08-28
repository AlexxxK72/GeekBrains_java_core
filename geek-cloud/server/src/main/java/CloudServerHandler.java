import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CloudServerHandler extends ChannelInboundHandlerAdapter {
    private DbHelper db;
    private FileHelper fh;
    private final String ROOT = "D:\\Cloud\\";

    public CloudServerHandler() {
        db = DbHelper.getInstance();
        fh = FileHelper.getInstance();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected...");
        // Send greeting for a new connection.
        // ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        // ctx.write("It is " + new Date() + " now.\r\n");
        // ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null)
                return;
            System.out.println(msg.getClass());
            if (msg instanceof CommandMessage) {
                CommandMessage cMsg = (CommandMessage) msg;
                CommandMessage.Commands cmd = cMsg.getCmd();
                Object[] args = cMsg.getArgs();
                switch (cmd) {
                    case Auth: {
                        Boolean isAuthorized = db.checkUser(args[0].toString(), args[1].toString());
                        String path = (isAuthorized) ? db.getPathCloud(args[0].toString(), args[1].toString()) : null;
                        ctx.write(new CommandMessage(CommandMessage.Commands.Auth_Result,
                                new Object[]{isAuthorized, args[0], args[1], ROOT + path + "\\"}));
                        ctx.flush();
                    }
                    break;
                    case LogOut: {
                        ctx.write(cMsg);
                        ctx.flush();
                    }
                    break;
                    case List: {
                        ctx.write(new CommandMessage(CommandMessage.Commands.List,
                                new Object[]{fh.getListFile(args[0].toString())}));
                        ctx.flush();
                    }
                    break;
                    case IsDirectory: {
                        ctx.write(new CommandMessage(CommandMessage.Commands.IsDirectory,
                                new Object[]{fh.isDirectory(args[0].toString())}));
                        ctx.flush();
                    }
                    break;
                    case Delete: {
                        Path pathFile = Paths.get(args[0].toString());
                        fh.deleteFiles(pathFile);
                        ctx.write(new CommandMessage(CommandMessage.Commands.List,
                                new Object[]{fh.getListFile(pathFile.getParent().toString())}));
                    }

                    break;
                    case Rename: {
                        fh.renameFile(args[0].toString(), args[1].toString());
                    }
                    break;
                    case Upload: {
                        fh.placeFile(args[0].toString(), (byte[]) args[1]);
                        Path pathFile = Paths.get(args[0].toString()).getParent();
                        ctx.write(new CommandMessage(CommandMessage.Commands.List,
                                new Object[]{fh.getListFile(pathFile.toString())}));
                        ctx.flush();
                    }
                    break;
                    case Download: {
                        Path pathFile = Paths.get(args[0].toString());
                        byte[] file = fh.fileToByteArray(pathFile);
                        if (file != null) {
                            ctx.write(new CommandMessage(CommandMessage.Commands.Download,
                                    new Object[]{pathFile.getFileName().toString(), file}));
                            ctx.flush();
                        }
                    }
                    break;
                }
            } else {
                System.out.printf("Server received wrong object!");
                return;
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.flush();
        ctx.close();
    }
}
